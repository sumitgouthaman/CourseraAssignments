#!/usr/bin/python -tt

import sys
import json

def extract_count(hashtag_tuple):
    return hashtag_tuple[1]

def main():
    tweet_file = open(sys.argv[1])
    hashtag_counts = {}
    for tweets in tweet_file:
        tweets_dict = json.loads(tweets)
        if u'entities' in tweets_dict:
            entities  = tweets_dict[u'entities']
            if u'hashtags' in entities:
                hashtags = entities[u'hashtags']
                for hashtag in hashtags:
                    term = hashtag[u'text']
                    if term not in hashtag_counts:
                        hashtag_counts[term] = 1
                    else:
                        hashtag_counts[term] += 1
    hashtag_counts = hashtag_counts.items()
    sorted_counts = sorted(hashtag_counts, key=extract_count, reverse=True)
    count = 0
    for sc in sorted_counts:
        print sc[0], sc[1]
        count = count + 1
        if count == 10:
            break
    
if __name__ == '__main__':
    main()
