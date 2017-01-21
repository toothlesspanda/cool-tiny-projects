from proj import FatTopo
from mininet.net import Mininet
topo = FatTopo(fanout=4)
net = Mininet(topo)
net.start()
dumpNodeConnections(net.hosts)
net.pingAll()
net.stop()
