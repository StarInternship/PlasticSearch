using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SimpleSearch.models;

namespace SimpleSearch
{
    class Program
    {
        static void Main(string[] args)
        {
            Importer importer = new Importer();
            Dictionary<string, string> files = importer.ReadFiles();

            foreach (KeyValuePair<string, string> entry in files)
            {
                Console.WriteLine(entry.Key + " 's text = " + entry.Value);
            }

            Console.ReadLine();
        }
    }
}
