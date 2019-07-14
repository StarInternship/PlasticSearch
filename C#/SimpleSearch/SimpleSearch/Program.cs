using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SimpleSearch.models;
using SimpleSearch.models.tokenizer;

namespace SimpleSearch
{
    class Program
    {
        static Tokenizer exactSearchTokenizer = new ExactSearchTokenizer();
        static Tokenizer ngramSearchTokenizer = new NgramSearchTokenizer();

        static List<string> result;
        static List<string> queryTokens;
        // static Search search = new Search();
        static void Main(string[] args)
        {

            Preprocess();

            while (true)
            {
                string query = Console.ReadLine();

                QueryProcess(query);

                ShowResult(result);

            }

        }

        static void Preprocess()
        {
            Importer importer = new Importer();

            Dictionary<string, string> files = importer.ReadFiles();

            foreach (KeyValuePair<string, string> pair in files)
            {
                string cleanText = ngramSearchTokenizer.CleanText(pair.Value);
                //TODO: ahmad:)
            }
        }

        static void QueryProcess(string query)
        {

        }

        static void DoSearch()
        {

        }

        static void ShowResult(List<string> result)
        {
            foreach(string fileName in result)
            {
                Console.WriteLine(fileName);
            }
        }
    }
}