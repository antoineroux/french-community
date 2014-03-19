Graph the french dev community
==============================

This is a basic attempt to visualize the french dev community. It is based on the ranking of french devs made by Human Coders: http://ranking.humancoders.com/

The attempt is to build a graph. The core of the graph is made of those 50 Twitter accounts. The rest of the graph would be made of their followers. Each link would represent a follow relationship. The idea behind it is to consider that these people are at the core of their respective communities and to show their respective size and whether some people belong to several communities.

Currently, the project contains a script to collect the data (collect-json.groovy), a generated JSON file of ~15 MB and an index.html page to view it with d3.js. The data is too large to be displayed (264470 nodes). So to move further, it needs some refining. A possible solution would be to count the links and to give them different weights, so that it would be possible to aggregate several nodes into a single one.
