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

        static void Main(string[] args)
        {


            while (true)
            {
                string query = Console.ReadLine();

                

            }

        }

        static void preprocess()
        {
            Importer importer = new Importer();

            Dictionary<string, string> files = importer.ReadFiles();

            foreach (KeyValuePair<string, string> pair in files)
            {
                string cleanText = ngramSearchTokenizer.CleanText(pair.Value);
                //TODO: ahmad:)
            }
        }
    }
}