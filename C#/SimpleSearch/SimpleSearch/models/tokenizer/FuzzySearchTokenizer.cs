using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models.tokenizer
{
    class FuzzySearchTokenizer : Tokenizer
    {
        private static readonly int FUZZINESS = 1;
        private static readonly IList<char> characters = new List<char>();

        public override ISet<string> Tokenize(string text)
        {
            throw new NotImplementedException();
        }
        public override LinkedList<string> Develope(string token)
        {
            throw new NotImplementedException();
        }
    }
}
