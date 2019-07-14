using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace SimpleSearch.models.tokenizer
{
    class NgramSearchTokenizer : Tokenizer
    {
        private static readonly int MIN = 3;
        private static readonly int MAX = 30;

        public override ISet<string> Tokenize(string text)
        {
            ISet<string> tokenSet = new HashSet<string>();

            Regex.Split(text, SPLITTER).ToList().ForEach(token =>
            {
                int max = Math.Min(MAX, token.Length);

                for (int length = MIN; length <= MAX; length++)
                {
                    for (int start = 0; start + length <+ token.Length; start++)
                    {
                        tokenSet.Add(token.Substring(start, start + length));
                    }
                }
            });

            return tokenSet;
        }
        public override List<string> Develope(string token) => (new string[] { token }).ToList();
    }
}
