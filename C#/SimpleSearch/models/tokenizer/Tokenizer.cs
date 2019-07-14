﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models.tokenizer
{
    abstract class Tokenizer
    {
        protected static readonly string SPLITTER = "\\s";

        public string CleanText(string text) => text.ToLower();

        public abstract void tokenizeData(string filePath, string text, IDictionary<string, IDictionary<string, int>> data);

        public abstract ISet<string> TokenizeQuery(string text);

        public abstract List<string> Develope(string token);
    }
}