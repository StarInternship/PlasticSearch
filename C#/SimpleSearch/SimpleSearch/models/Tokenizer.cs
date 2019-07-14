using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models
{
    abstract class Tokenizer
    {
        public string CleanText(string text) => text.ToLower();

        public abstract ISet<string> Tokenize(string text);

        public abstract LinkedList<string> Develope(string token);
    }
}
