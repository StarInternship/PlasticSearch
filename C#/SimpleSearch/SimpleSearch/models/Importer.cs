using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models
{
    class Importer
    {
        private readonly string filesPath = "C:\\Users\\hadi\\source\\repos\\SimpleSearch\\C#\\SimpleSearch\\SimpleSearch\\test_files";

        public Dictionary<string, string> ReadFiles()
        {
            Dictionary<string, string> files = new Dictionary<string, string>();
            ReadFile(filesPath,files);
            return files;
        }

        public void ReadFile(string path,Dictionary<string , string> filesDictionary)
        {
            if (Directory.Exists(path))
            {
                string[] files = Directory.GetFiles(path);
                foreach (string filePath in files)
                {
                    ReadFile(filePath, filesDictionary);
                }
                return;
            }
            if (File.Exists(path))
            {
                string text = File.ReadAllText(path);
                filesDictionary[path] = text;
            }
        }
    }
}
