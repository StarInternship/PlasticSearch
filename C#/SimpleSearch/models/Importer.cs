﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models
{
    class Importer
    {
        private readonly string filesPath = @"../../../../test_files";

        public Dictionary<string, string> ReadFiles()
        {
            Dictionary<string, string> files = new Dictionary<string, string>();
            ReadDirectory(filesPath, files);
            return files;
        }

        public void ReadFile(string path, Dictionary<string, string> filesDictionary)
        {
            if (File.Exists(path))
            {
                string text = File.ReadAllText(path);
                filesDictionary[path] = text;
                Console.WriteLine(path + " -> " + text);
            }
        }

        public void ReadDirectory(string path, Dictionary<string, string> filesDictionary)
        {
            if (Directory.Exists(path))
            {
                string[] files = Directory.GetFiles(path);
                foreach (string filePath in files)
                {
                    ReadFile(filePath, filesDictionary);
                }
                string[] directories = Directory.GetDirectories(path);
                foreach (string filePath in directories)
                {
                    ReadDirectory(filePath, filesDictionary);
                }
            }
        }
    }
}
