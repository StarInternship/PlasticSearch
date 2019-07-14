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
        private readonly IDictionary<string, ISet<string>> ngramTokensList = new Dictionary<string, ISet<string>>();
        private readonly IDictionary<string, ISet<string>> exactTokensList = new Dictionary<string, ISet<string>>();
        private readonly IDictionary<ListType, IDictionary<string, ISet<string>>> lists = new Dictionary<ListType, IDictionary<string, ISet<string>>>();
        private IDictionary<string, ISet<string>> currentList;
        private Tokenizer queryTokenizer;

        public Search()
        {
            lists[ListType.EXACT] = exactTokensList;
            lists[ListType.NGRAM] = ngramTokensList;
            lists[ListType.FUZZY] = exactTokensList;
        }

        public void InsertNgram(string filePath, ISet<string> tokens)
        {
            ngramTokensList[filePath] = tokens;
        }

        public void InsertExact(string filePath, ISet<string> tokens)
        {
            exactTokensList[filePath] = tokens;
        }

        public void setQueryTokenizer(Tokenizer queryTokenizer, ListType type)
        {
            this.queryTokenizer = queryTokenizer;
            currentList = lists[type];
        }

        public void search(List<string> queryTokens, int index, List<string> filePaths, ISet<string> result)
        {
            if (filePaths == null)
            {
                filePaths = new List<string>(currentList.Keys);
            }
            if (filePaths.Count == 0)
            {
                return;
            }
            if (queryTokens.Count == index)
            {
                result.UnionWith(filePaths);
                return;
            }

            string currentToken = queryTokens[index];
            List<string> developedTokens = queryTokenizer.Develope(currentToken);

            foreach (string token in developedTokens)
            {
                List<string> newFilePaths = new List<string>();

                foreach (string filePath in filePaths)
                {
                    if (currentList[filePath].Contains(token))
                    {
                        newFilePaths.Add(filePath);
                    }
                }

                search(queryTokens, index + 1, newFilePaths, result);
            }
        }
    }

    public enum ListType
    {
        EXACT, NGRAM, FUZZY
    }
}
