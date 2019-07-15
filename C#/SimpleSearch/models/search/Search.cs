﻿using SimpleSearch.models.tokenizer;
using System.Collections.Generic;

namespace SimpleSearch.models.search
{
    class Search
    {
        public IDictionary<string, InvertedIndex> ExactData { get; } = new Dictionary<string, InvertedIndex>();
        public IDictionary<string, InvertedIndex> NgramData { get; } = new Dictionary<string, InvertedIndex>();
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

        private void findFiles(string queryToken, ISet<string> foundFilePaths, Tokenizer queryTokenizer, IDictionary<string, InvertedIndex> data)
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
