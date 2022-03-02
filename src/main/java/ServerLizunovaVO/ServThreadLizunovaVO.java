package ServerLizunovaVO;

import DatabaseLizunovaVO.DBHandlerLizunovaVO;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServThreadLizunovaVO extends Thread {
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Socket client;

    public ServThreadLizunovaVO(Socket socket) {
        this.client = socket;
        InetAddress address = socket.getInetAddress();
        try {
            printStream = new PrintStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String clientMassage;
        try {
            ObjectInputStream sois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream soos = new ObjectOutputStream(client.getOutputStream());
            DBHandlerLizunovaVO dbHandler = new DBHandlerLizunovaVO();
            ResponseServiceLizunovaVO resp=new ResponseServiceLizunovaVO();
            while ((clientMassage = (String) sois.readObject()) != null) {
                switch (clientMassage) {
                    case "статусы":
                        resp.getStatuses(sois, soos);
                        break;
                    case "исполнители":
                        resp.getEmployees(sois, soos);
                        break;
                    case "проекты":
                        resp.getProjects(sois, soos);
                        break;
                    case "позиции":
                        resp.getPositions(sois, soos);
                        break;
                    case "департаменты":
                        resp.getDepartments(sois, soos);
                        break;
                    case "области разработки":
                        resp.getDevelopmentAreas(sois, soos);
                        break;
                    case "роли":
                        resp.getRoles(sois, soos);
                        break;
                    case "вход":
                        resp.Login(sois, soos);
                        break;
                    case "регистрация":
                    case "добавить пользователя":
                        resp.Registration(sois, soos);
                        break;
                   case "все пользователи":
                        resp.AllUsers(sois, soos);
                        break;
                    case "удалить пользователя":
                        resp.DeleteUser(sois, soos);
                        break;
                    case "записать время":
                        resp.UpdateTime(sois, soos);
                        break;
                   case "сохранить":
                        resp.Save(sois, soos);
                        break;
                   case "главная":
                        resp.MainWindow(sois, soos);
                        break;
                   case "новые данные":
                        resp.AddTaskData(sois, soos);
                        break;
                   case "удалить данные":
                        resp.DeleteTaskData(sois, soos);
                        break;
                   case "cохранение изменений":
                        resp.EditTaskData(sois, soos);
                       break;
                    case "изменить сотрудника":
                        resp.EditEmployeeData(sois, soos);
                        break;
                    case "добавить сотрудника":
                        resp.AddEmployeeData(sois, soos);
                        break;
                    case "удалить сотрудника":
                        resp.DeleteEmployeeData(sois, soos);
                        break;
                   case "заблокировать":
                        resp.Block(sois, soos);
                        break;
                   }
                }
            } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private void disconnect() {
        try {
            if (printStream != null) {
                printStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            System.out.println("______disconnected______" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}