import java.io.BufferedReader;
import java.io.InputStreamReader;

import Demo.Response;

public class Client {
    public static void main(String[] args) {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.client",
                extraArgs)) {
            // com.zeroc.Ice.ObjectPrx base =
            // communicator.stringToProxy("SimplePrinter:default -p 10000");
            Response response = null;
            Demo.PrinterPrx service = Demo.PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));

            if (service == null) {
                throw new Error("Invalid proxy");
            }

            String whoami = System.getProperty("user.name");
            String hostname = null;

            try {
                hostname = java.net.InetAddress.getLocalHost().getHostName();
            } catch (java.net.UnknownHostException ex) {
                System.out.println("Hostname can not be resolved");
            }
            System.out.println("Enter a string to send to the server:");

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                while (true) {
                    String message = br.readLine();
                    if (message.equals("exit")) {
                        break;
                    }
                    response = service.printString(whoami + ":" + hostname + ": " + message);
                    System.out.println("Respuesta del server: \n\n" + response.value + "\n" + response.responseTime);
                }
                br.close();
            } catch (Exception e) {
            }
        }
    }
}