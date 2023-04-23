package com.ibm.swat.samples.mq;

import com.ibm.msg.client.jms.JmsFactoryFactory;

import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import java.net.URL;
import java.net.MalformedURLException;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQConnectionFactoryFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;

import com.ibm.msg.client.wmq.WMQConstants;

public class JmsClient {

    private String MQ_HOST;
    private int MQ_PORT=1414;
    private String MQ_CHANNEL;
    private String MQ_QMGR;
    private String MQ_QUEUE;
    private String MQ_USER;
    private String MQ_PASSWORD;

    private URL ccdt;


    public static void main(String[] args) {
        JmsClient client = new JmsClient();
        client.init();
        try
        {
           
            client.check();    
        }
        catch(Exception e)
        {
            System.out.println("Configuation error : " + e.getMessage());
            System.exit(1);
        }
        try{
            JmsConnectionFactory cf = client.getConnectionFactory();
            JMSContext context = cf.createContext();
            Destination destination = context.createQueue("queue:///" + client.MQ_QUEUE);

            if(args.length<=0) {
                System.out.println("Missing expected action (send  or receive)");
                System.exit(1);
            }
            if (args[0].equals("send")){
                if(args.length<1 || args[1]==null){
                    System.out.println("missing message to send");
                    System.exit(1);
                }

                TextMessage message = context.createTextMessage(args[1]);

			    JMSProducer producer = context.createProducer();
			    producer.send(destination, message);
			    System.out.println("Sent message:\n" + message);
                System.exit(0);
            }
            else if (args[0].equals("receive"))
            {
                JMSConsumer consumer = context.createConsumer(destination); // autoclosable
			    String receivedMessage = consumer.receiveBody(String.class, 15000); // in ms or 15 seconds

			    System.out.println("\nReceived message:\n" + receivedMessage);
                System.exit(0);
            }
            else{
                System.out.println("Wrong action");
                System.exit(1);
            }

        }
        catch(JMSException j)
        {
            System.out.println("JMS Error: " + j.getMessage());
            System.exit(1);
        }

    }

    private void init()  {
        MQ_HOST = System.getenv("MQ_HOST");
        String port = System.getenv("MQ_PORT");
        if (port != null) MQ_PORT = Integer.parseInt(port);
        MQ_CHANNEL = System.getenv("MQ_CHANNEL");
        MQ_QMGR = System.getenv("MQ_QMGR");
        MQ_QUEUE = System.getenv("MQ_QUEUE");
        MQ_USER = System.getenv("MQ_USER");
        MQ_PASSWORD = System.getenv("MQ_PASSWORD");

    }

    private void check() throws JmsClientException {
        if(MQ_HOST == null) throw new JmsClientException("MQ_HOST not set");
        if(MQ_PORT  <= 0 ) throw new JmsClientException("MQ_PORT not valid");
        if(MQ_CHANNEL == null) throw new JmsClientException("MQ_CHANNEL not set");
        if(MQ_QMGR == null) throw new JmsClientException("MQ_MQGR not set");
        if(MQ_QUEUE == null) throw new JmsClientException("MQ_QUEUE not set");
        if(MQ_USER == null) throw new JmsClientException("MQ_USER not set");
        if(MQ_PASSWORD == null) throw new JmsClientException("MQ_PASSWORD not set");
    }

    private JmsConnectionFactory getConnectionFactory() throws JMSException  {
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
	
        JmsConnectionFactory cf = ff.createConnectionFactory();
        
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, MQ_HOST);
	    cf.setIntProperty(WMQConstants.WMQ_PORT, MQ_PORT);
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, MQ_CHANNEL);
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, MQ_QMGR);
		cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		cf.setStringProperty(WMQConstants.USERID, MQ_USER);
		cf.setStringProperty(WMQConstants.PASSWORD, MQ_PASSWORD);
        cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "TLS_RSA_WITH_AES_128_CBC_SHA256");
        return cf;
    }
}

class JmsClientException extends Exception{
    /**
     *
     */
    private static final long serialVersionUID = -8311633016425725135L;

    JmsClientException(){
        super();
    }
    JmsClientException(String msg){
        super(msg);
    }

}