URL = {
    'base': 'https://{proxy}.api.pvp.net/api/lol/{region}/{url}',
    'summoner_by_name': 'v{version}/summoner/by-name/{names}',
    'league_by_summoner': 'v{version}/league/by-summoner/{summonerids}',
    'ranked_stats': 'v1.3/stats/by-summoner/{summonerId}/ranked',
    'static_data': 'v{version}/champion/{cid}'
}

API_VERSIONS = {
    'summoner': '1.4',
    'stats': '1.3',
    'game' : '1.3',
    'league' : '2.5',
    'static-data': '1.2'
}

REGIONS = {
    'North_America' : 'na' 
}
