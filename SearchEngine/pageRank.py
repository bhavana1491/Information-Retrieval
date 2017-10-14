import networkx as nx
G = nx.read_edgelist("edgeList.txt", create_using=nx.DiGraph())
pr = nx.pagerank(G,alpha=0.85, personalization=None, max_iter=30, tol=1e-06, nstart=None, weight='weight',dangling=None)
op_file = open("external_pageRankFile.txt","w")
for key in pr.keys():

        op_file.write("path to index pages" + key + "=" + str(pr[key]) + "\n")

op_file.close()