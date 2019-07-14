using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using SimpleSearch.models;
using SimpleSearch.models.search;
using SimpleSearch.models.tokenizer;

namespace SimpleSearch
{
    class Program
    {
        private static readonly Tokenizer exactSearchTokenizer = new ExactSearchTokenizer();
        private static readonly Tokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
        private static readonly Search search = new Search();
        private static ISet<string> result;
        private static ISet<string> queryTokens;
        private static double currentTime;

        static void Main(string[] args)
        {
            Preprocess();

            while (true)
            {
                string query = Console.ReadLine();
                result = new HashSet<string>();

                QueryProcess(query);
                DoSearch();
                ShowResult(result);
            }
        }

        static void Preprocess()
        {
            Importer importer = new Importer();

            IDictionary<string, string> files = importer.ReadFiles();

            currentTime = MilliTime();

            foreach (KeyValuePair<string, string> pair in files)
            {
                string cleanText = ngramSearchTokenizer.CleanText(pair.Value);
                ngramSearchTokenizer.tokenizeData(pair.Key, cleanText, search.NgramData);
                exactSearchTokenizer.tokenizeData(pair.Key, cleanText, search.ExactData);
            }

            Console.WriteLine("Preprocess duration: " + (MilliTime() - currentTime) + " ms");
        }

        static void QueryProcess(string query)
        {
            currentTime = MilliTime();

            queryTokens = exactSearchTokenizer.TokenizeQuery(ngramSearchTokenizer.CleanText(query));

            Console.WriteLine("Query process duration: " + (MilliTime() - currentTime) + " ms");
        }

        static void DoSearch()
        {
            currentTime = MilliTime();

            search.search(queryTokens.ToList(), result);

            Console.WriteLine("Search duration: " + (MilliTime() - currentTime) + " ms");
        }

        static void ShowResult(ISet<string> result)
        {
            foreach(string fileName in result)
            {
                Console.WriteLine(fileName);
            }
        }

        private static double MilliTime()
        {
            long nano = 10000L * Stopwatch.GetTimestamp();
            nano /= TimeSpan.TicksPerMillisecond;
            nano *= 100L;
            return nano / 1000000.0;
        }
    }
}