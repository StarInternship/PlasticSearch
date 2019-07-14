using SimpleSearch.models.tokenizer;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SimpleSearch.models.search
{
    class Search
    {
        public IDictionary<string, IDictionary<string, int>> ExactData { get; } = new Dictionary<string, IDictionary<string, int>>();
        public IDictionary<string, IDictionary<string, int>> NgramData { get; } = new Dictionary<string, IDictionary<string, int>>();
        private readonly Tokenizer exactSearchTokenizer = new ExactSearchTokenizer();
        private readonly Tokenizer ngramSearchTokenizer = new NgramSearchTokenizer();
        private readonly Tokenizer fuzzySearchTokenizer = new FuzzySearchTokenizer();

        public void search(List<string> queryTokens, ISet<string> result)
        {
            bool first = true;
            foreach (string queryToken in queryTokens)
            {
                ISet<string> foundFilePaths = new HashSet<string>();

                findFiles(queryToken, foundFilePaths, exactSearchTokenizer, ExactData);
                findFiles(queryToken, foundFilePaths, ngramSearchTokenizer, NgramData);
                findFiles(queryToken, foundFilePaths, fuzzySearchTokenizer, ExactData);

                if (first)
                {
                    result.UnionWith(foundFilePaths);
                    first = false;
                } else
                {
                    result.IntersectWith(foundFilePaths);
                }
            }
        }

        private void findFiles(string queryToken, ISet<string> foundFilePaths, Tokenizer queryTokenizer, IDictionary<string, IDictionary<string, int>> data)
        {
            List<string> developedTokens = queryTokenizer.Develope(queryToken);

            developedTokens.ForEach(token =>
            {
                if (data.ContainsKey(token))
                {
                    foundFilePaths.UnionWith(data[token].Keys);
                }
            });
        }
    }
}
