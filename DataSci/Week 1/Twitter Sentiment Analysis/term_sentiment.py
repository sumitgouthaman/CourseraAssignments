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
            word_scores = []
            for word in words:
                if(word in scores):
                    word_scores.append(scores[word])
                else:
                    word_scores.append(None)
            for i in range (0, len(words)):
                if word_scores[i] == None:
                    term_score = 0
                    for j in range (0, len(words)):
                        if i == j or word_scores[j] == None:
                            continue
                        dist = abs(i - j)
                        term_score += dist * word_scores[j]
                    print words[i], term_score
                        
if __name__ == '__main__':
    main()
