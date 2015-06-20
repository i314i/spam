package myspam;

import java.net.*;
import java.io.*;
public class MainSpam {
	private static SmtpThread smtp;
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int num;
		String targetEmail;
		String subject;
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		/*System.out.print("How many spams?");
		num=Integer.parseInt(br.readLine());*/
		System.out.print("Input how many times:");
		num = Integer.parseInt(br.readLine());
		System.out.print("Input target e-mail:");
		targetEmail = br.readLine();
		System.out.print("Input e-mail subject:");
		subject=br.readLine();
		for(int i=0;i<num;i++){
			smtp = new SmtpThread("*****", i+"@*******", targetEmail, subject, "thanks for your idea!!!");
	        smtp.start();
	        Thread.sleep(1000);
	        /*if(i%100==0)
	        	Thread.sleep(60000);*/
		}
	}

}

class SmtpThread extends Thread {
    private String server, from, to, subject, body;
 
    public SmtpThread(String sv, 
                      String f, String t, String sub,
                      String tx) {
        server = sv;   // mail server
        from = f;      // sender
        to = t;        // receiver
        subject = sub; // 
 
        // spam body
        body = "From: " + from + "\n" +
               "To: " + to + "\n" +
               "Subject: " + subject +
               "\n\n" + tx + "\n";
    }
 
    public void run() {
        BufferedReader buf;
        PrintStream os;
        String rmsg;
        Socket skt;
        int state = 0; //
 
        try {
            skt = new Socket(server, 25);
            buf = new BufferedReader(new
                        InputStreamReader(skt.getInputStream()));
            os = new PrintStream(skt.getOutputStream());
 
            while((rmsg = buf.readLine()) != null) {
                System.out.println(rmsg);
                if(state == 0 && rmsg.charAt(0) == '2') {
                    os.print("HELO there\r\n");
                    state++;
                }
                else if(state == 1 && rmsg.charAt(0) == '2') {
                    os.print("MAIL FROM: <" + from + ">\r\n");
                    state++;
                }
                else if(state == 2 && rmsg.charAt(0) == '2') {
                    os.print("RCPT TO: <" + to + ">\r\n");
                    state++;
                }
                else if(state == 3 && rmsg.charAt(0) == '2') {
                    os.print("DATA\r\n");
                    state++;
                }
                else if(state == 4 && rmsg.charAt(0) == '3') {
                    os.print(body + "\r\n.\r\n");
                    state++;
                }
                else if(state == 5 && rmsg.charAt(0) == '2') {
                    os.print("QUIT\r\n");
                    state++;
                }
                else if(state == 6 && rmsg.charAt(0) == '2') {
                    System.out.println("¶l¥ó¶Ç°e§¹²¦");
                    break;
                }
                else {
                    //do nothing
                }
            }
            skt.close();
        }
        catch(UnknownHostException e) {
            System.out.println(e.toString());
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
    }
}
