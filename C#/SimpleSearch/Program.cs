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
        private static readonly Stopwatch sw = new Stopwatch();

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

            sw.Start();

            foreach (var pair in files)
            {
                string cleanText = ngramSearchTokenizer.CleanText(pair.Value);
                ngramSearchTokenizer.tokenizeData(pair.Key, cleanText, search.NgramData);
                exactSearchTokenizer.tokenizeData(pair.Key, cleanText, search.ExactData);
            }

            sw.Stop();
            Console.WriteLine("Preprocess duration: " + sw.ElapsedMilliseconds + " ms");
        }

        static void QueryProcess(string query)
        {
            sw.Restart();

            queryTokens = exactSearchTokenizer.TokenizeQuery(ngramSearchTokenizer.CleanText(query));

            sw.Stop();
            Console.WriteLine("Query process duration: " + sw.ElapsedMilliseconds + " ms");
        }

        static void DoSearch()
        {
            sw.Restart();

            search.search(queryTokens.ToList(), result);

            sw.Stop();
            Console.WriteLine("Search duration: " + sw.ElapsedMilliseconds + " ms");
        }

        static void ShowResult(ISet<string> result)
        {
            if (result.Count == 0)
            {
                Console.WriteLine("Not found!");
                return;
            }
            foreach(string fileName in result)
            {
                Console.WriteLine(fileName);
            }
        }
    }
}