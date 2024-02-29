import java.io.BufferedReader;
import java.io.InputStreamReader;

import Demo.Response;

public class PrinterI implements Demo.Printer {
    public Response printString(String s, com.zeroc.Ice.Current current) {
        String[] command = s.split(" ");
        String clientDetails = command[0];

        String output = "";
        if (command[1].equals("exit")) {
            System.exit(0);
        } else if (command[1].equals("listifs")) {
            output = printLogicInterfaces(clientDetails);
        } else if (command[1].equals("listports")) {
            output = printPortsAndServices(clientDetails, command[2]);
        } else if (command[1].startsWith("!")) {
            String cmd = s.substring(s.indexOf("!") + 1);
            output = printCommandOutput(clientDetails, cmd);
        } else if (isNumber(command[1]) != -1) {
            output = fibonacci(clientDetails, isNumber(command[1]));
        } else {
            System.out.println("Invalid command");
        }

        return new Response(0, output);
    }

    private int isNumber(String n) {
        try {
            return Integer.parseInt(n);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public String fibonacci(String clientDetails, int n) {
        String output = clientDetails + "\n";
        String serie = "";
        int a = 0, b = 1, c;
        for (int i = 0; i < n; i++) {
            output += isPrime(a) ? a + " " : "";
            serie += a + " ";
            c = a + b;
            a = b;
            b = c;
        }

        System.out.println(clientDetails + "\n" + serie);
        return output;
    }

    public String printLogicInterfaces(String clientDetails) {
        String str = null, output = clientDetails + "\n";
        try {
            Process p = Runtime.getRuntime().exec("ipconfig");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null) {
                output += str + System.getProperty("line.separator");
            }
            System.out.println(output);
            br.close();
        } catch (Exception ex) {
        }
        return output;
    }

    public String printPortsAndServices(String clientDetails, String ipv4) {
        String str = null, output = clientDetails + "\n";
        try {
            Process p = Runtime.getRuntime().exec("nmap " + ipv4);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null) {
                output += str + System.getProperty("line.separator");
            }
            System.out.println(output);
            br.close();
        } catch (Exception ex) {
        }
        return output;
    }

    public String printCommandOutput(String clientDetails, String command) {
        String str = null, output = clientDetails + "\n";
        // System.out.println("Command: " + command);
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null) {
                output += str + System.getProperty("line.separator");
            }
            System.out.println("\n" + output);
            br.close();
        } catch (Exception ex) {
        }
        return output;
    }
}