__最简单的TCP以及二维码生成Demo__

***************************************工程说明**********************************
*TCPAmosDemo\app\libs*
core-3.3.3.jar 用于生成二维码
libtcpamos-release.aar 用于TCP连接处理

---------------------------------------how to use-----------------------------------

implements TCPAmosHelpListener
tcpAmosHelp=new TCPAmosHelp(this);
tcpAmosHelp.initConnect(ip,port,0);
//接收数据
    public void onTCPAmosReceive(Object o, int i)
//连接状态
    public void onTCPAmosStatusChanged(int i, int i1) 

