import requests
import RiotConsts as consts


class RiotAPI(object):
    #api constructure. Takes the api key and selects NA as a rigion if none specified
    def __init__(self, api_key, region=consts.REGIONS['North_America']):
        self.api_key = api_key
        self.region = region

    #pulls info from the url and formats it
    def _request(self, api_url, params={}):
        args = {'api_key': self.api_key} 
        for key, value in params.items():
            if key not in args:
                args[key] = value
        response = requests.get(
            consts.URL['base'].format(
                proxy=self.region,
                region=self.region,
                url=api_url
                ),
            params=args
            )
 
        return response.json()

    #requests for summoner info by name from api
    def get_summoner_by_name(self, name):
        api_url = consts.URL['summoner_by_name'].format(
            version=consts.API_VERSIONS['summoner'],
            names=name
            )
        return self._request(api_url)

    #requests league of summoners id info from api
    def get_summoner_league(self, s_id):
        api_url = consts.URL['league_by_summoner'].format(
            version=consts.API_VERSIONS['league'],
            summonerids=s_id
            )
        return self._request(api_url)

    def get_summoner_ranked_stats(self, sid):
        api_url = consts.URL['ranked_stats'].format(
            summonerId=sid
            )
        return self._request(api_url)       
    
    def get_champion_name(self, c_id):
        api_url = consts.URL['static_data'].format(
            version=consts.API_VERSIONS['static-data'],
            cid=c_id
            )
        return self._request(api_url)        
