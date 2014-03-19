@Grab("org.twitter4j:twitter4j-core:4.0.0")
import twitter4j.*
import twitter4j.auth.*
import groovy.transform.*
import groovy.json.*


def mostFollowed = ["romainguy", "fabpot", "RudyHuyn", "gsempe", "SachaGreif", "maxime", "HTeuMeuLeu", "glaforge", "goetter", "mattetti", "agoncal", "mchaize", "chanezon", "laurentchemla", "fabienpenso", "cyrilmottier", "RomainKey", "emmanuelbernard", "lrz", "joffrey", "glazou", "solomonstre", "jeremymarc", "mickael", "jedisct1", "nmartignole", "ylechelle", "mathemagie", "aeden", "sylvainw", "alexismp", "waxzce", "macournoyer", "n1k0", "clementj", "g123k", "oncletom", "juliendollon", "cgrand", "DidierGirard", "Stof70", "davrous", "aheritier", "Anseaume", "Woodgate", "francoisz", "SamyRabih", "ncannasse", "geoffrey_crofte", "amicel"]

def twitter = TwitterFactory.getSingleton()
twitter.setOAuthConsumer("Provide your own", "Provide your own")
//def requestToken = twitter.getOAuthRequestToken();

def accessToken = new AccessToken("Provide your own", 
								  "Provide your own",
								  1)

twitter.setOAuthAccessToken(accessToken)

def requestCount = 0

def users = twitter.lookupUsers(mostFollowed.toArray() as String[])
requestCount++

def followers = []
def nodes = [] as HashSet
def links = []
users.each { user ->
	println "Collecting followers of $user.screenName"
	def followedNode = new Node(user.id.toString())
	nodes << followedNode

	long cursor = -1
	while(cursor != 0) {
		def results = twitter.getFollowersIDs(user.screenName, cursor)
		cursor = results.nextCursor

		requestCount++
		if(requestCount == 15) { println "pausing"; Thread.sleep(15 * 60 * 1000); requestCount = 0 }

	 	followers += results.getIDs().toList()
	 	results.getIDs().each {
	 		nodes << new Node(it.toString())
	 	}
	 	def nodesList = nodes.toList()
	 	results.getIDs().each {
	 		links << new Link(
	 			source: nodesList.indexOf(new Node(it.toString())), 
	 			target: nodesList.indexOf(followedNode)
	 		)
	 	}
	}
}

println followers.size()

def jsonBuilder = new JsonBuilder()
jsonBuilder(nodes: nodes, links: links)

new File("data.json") << jsonBuilder.toString()


class Link {
	int source
	int target
	int value = 1 

}

@Immutable
class Node {
	String name
}