#!/usr/bin/python -tt

import sys
import json

def main():
    tweet_file = open(sys.argv[1])
    terms = {}
    for tweets in tweet_file:
        tweets_dict = json.loads(tweets)
        if u'text' in tweets_dict.keys():
            tweet = tweets_dict[u'text']
            words = tweet.split()
            for word in words:
                if word not in terms:
                    terms[word] = 1
                else:
                    terms[word] = terms[word] + 1
    total_count = 0
    for v in terms.values(): total_count += v
    for word in terms:
        print word, float(terms[word]) / total_count

if __name__ == '__main__':
    main()
