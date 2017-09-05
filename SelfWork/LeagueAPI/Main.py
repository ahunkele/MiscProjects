from RiotAPI import RiotAPI
from characters import champs

def main():
    #apikey 
    api = RiotAPI('6a4f80c7-39db-45df-b5eb-5357f54d8c80')
    
    print('please enter a summoner name to lookup!')

    #summoners name 
    summoner_name = raw_input('>> ')

    #pulls basic info about the summoners name like ID 
    r = api.get_summoner_by_name(summoner_name)


    #summoners id
    summoner_id = r[summoner_name]['id']
    
    #concatenates id to a string
    ID = str(summoner_id)
    print ID

    #pulls stats from a players ranked games
    r_stats = api.get_summoner_ranked_stats(ID)

    """
    looks up the whole json file of the summoners league
    you can request to look up ['tier'](bronze, silver, gold, etc)
    or you can see the bracket['queue']
    """ 
    summoner_league_lookup = api.get_summoner_league(summoner_id)

    #looks up what bracket the summoner is queueing in
    queue_bracket = summoner_league_lookup[ID][0]['queue']

    #Styalizes the output to something nicer 
    if queue_bracket == 'RANKED_SOLO_5x5':
        queue_bracket = 'Solo Queue'

    #loops through players stats of the ranks season and pulls all the champions into ids then turns the id into a name and looks up the total pentakills achived on a champion this season
    for c in r_stats['champions']:
        print "Champion " + champs[c['id']] + "'s Number of PentaKills " + str(c['stats']['totalPentaKills'])
   
    #Asks user if they'd like to search again
    print "Look up another person> Yes or No"
    check = raw_input('>> ')

    if check == 'Yes':
        main()
    elif check == 'yes':
        main()
    elif check == 'YES':
        main()
    else:
        print "Thanks for searching! "


if __name__ == '__main__':
    main()
