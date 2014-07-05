#!/usr/bin/python -tt

import sys
import json

def main():
    sent_file = open(sys.argv[1])
    tweet_file = open(sys.argv[2])
    scores = {}
    for line in sent_file:
        term, score  = line.split("\t")
        scores[term] = int(score)
    for tweets in tweet_file:
        tweets_dict = json.loads(tweets)
        if u'text' in tweets_dict.keys():
            tweet = tweets_dict[u'text']
            words = tweet.split()
            tweet_score = 0
            for word in words:
                if word in scores:
                    tweet_score += scores[word]
            print tweet_score

if __name__ == '__main__':
    main()
