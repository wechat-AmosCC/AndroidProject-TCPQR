__��򵥵�TCP�Լ���ά������Demo__

***************************************����˵��**********************************
*TCPAmosDemo\app\libs*
core-3.3.3.jar �������ɶ�ά��
libtcpamos-release.aar ����TCP���Ӵ���

---------------------------------------how to use-----------------------------------

implements TCPAmosHelpListener
tcpAmosHelp=new TCPAmosHelp(this);
tcpAmosHelp.initConnect(ip,port,0);
//��������
    public void onTCPAmosReceive(Object o, int i)
//����״̬
    public void onTCPAmosStatusChanged(int i, int i1) 

