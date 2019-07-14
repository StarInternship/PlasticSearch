using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace SimpleSearch.models.tokenizer
{
    class ExactSearchTokenizer : Tokenizer
    {
        public override ISet<string> Tokenize(string text) => Regex.Split(text, SPLITTER).ToHashSet();

        public override LinkedList<string> Develope(string token) => new LinkedList<string>((new string[] {token}).ToList());
    }
}