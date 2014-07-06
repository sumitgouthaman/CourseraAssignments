#!/usr/bin/python -tt

import sys
import json
import re

def main():
    sent_file = open(sys.argv[1])
    tweet_file = open(sys.argv[2])
    scores = {}
    for line in sent_file:
        term, score  = line.split("\t")
        scores[term] = int(score)
    happiness = {}
    for tweets in tweet_file:
        tweets_dict = json.loads(tweets)
        if u'text' in tweets_dict.keys():
            if u'user' in tweets_dict.keys():
                user_dict = tweets_dict[u'user']
                if u'location' in user_dict:
                    location = user_dict[u'location'].encode('utf-8')
                    state = getUSState(location)
                    if location == None:
                        continue
            tweet = tweets_dict[u'text']
            words = tweet.split()
            tweet_score = 0
            for word in words:
                if word in scores:
                    tweet_score += scores[word]
            if state in happiness:
                happiness[state] += tweet_score
            else:
                happiness[state] = tweet_score
    happiest_state = None
    max_happiness = None
    for state in  happiness.keys():
        if happiest_state == None:
            happiest_state = state
            max_happiness = happiness[state]
        else:
            if happiness[state] > max_happiness:
                max_happiness = happiness[state]
                happiest_state = state
    print happiest_state
                        
def getUSState(location):
    states = {
        'AK': 'Alaska',
        'AL': 'Alabama',
        'AR': 'Arkansas',
        'AS': 'American Samoa',
        'AZ': 'Arizona',
        'CA': 'California',
        'CO': 'Colorado',
        'CT': 'Connecticut',
        'DC': 'District of Columbia',
        'DE': 'Delaware',
        'FL': 'Florida',
        'GA': 'Georgia',
        'GU': 'Guam',
        'HI': 'Hawaii',
        'IA': 'Iowa',
        'ID': 'Idaho',
        'IL': 'Illinois',
        'IN': 'Indiana',
        'KS': 'Kansas',
        'KY': 'Kentucky',
        'LA': 'Louisiana',
        'MA': 'Massachusetts',
        'MD': 'Maryland',
        'ME': 'Maine',
        'MI': 'Michigan',
        'MN': 'Minnesota',
        'MO': 'Missouri',
        'MP': 'Northern Mariana Islands',
        'MS': 'Mississippi',
        'MT': 'Montana',
        'NA': 'National',
        'NC': 'North Carolina',
        'ND': 'North Dakota',
        'NE': 'Nebraska',
        'NH': 'New Hampshire',
        'NJ': 'New Jersey',
        'NM': 'New Mexico',
        'NV': 'Nevada',
        'NY': 'New York',
        'OH': 'Ohio',
        'OK': 'Oklahoma',
        'OR': 'Oregon',
        'PA': 'Pennsylvania',
        'PR': 'Puerto Rico',
        'RI': 'Rhode Island',
        'SC': 'South Carolina',
        'SD': 'South Dakota',
        'TN': 'Tennessee',
        'TX': 'Texas',
        'UT': 'Utah',
        'VA': 'Virginia',
        'VI': 'Virgin Islands',
        'VT': 'Vermont',
        'WA': 'Washington',
        'WI': 'Wisconsin',
        'WV': 'West Virginia',
        'WY': 'Wyoming'
    }

    locs = re.split('\W+', location)
    for loc in locs:
        if loc in states:
            return loc
        else:
            return None

if __name__ == '__main__':
    main()
