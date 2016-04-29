package atividadehibernate;

import DAO.ClienteDAO;
import DAO.NotaFiscalDAO;
import DAO.NotaFiscalItemDAO;
import DAO.ProdutoDAO;
import Models.Cliente;
import Models.NotaFiscal;
import Models.NotaFiscalitem;
import Models.Produto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class AtividadeHibernate {

    public static void main(String[] args) throws FileNotFoundException, 
            IOException, MessagingException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean a = true;
        while (a) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1 - Gerar Relatorio");
            System.out.println("2 - Sair");

            int codOpcao = Integer.parseInt(br.readLine());
            
            switch(codOpcao){
                case 1:
                    geraRelatorioExcel("relatorio");
                    
                    System.out.println("\n\nRelatorio Gerado com sucesso!\n");
                    System.out.println("\n\nEnviar relatorio por email? \n1 - Sim\n2 - Não");
                    
                    codOpcao = Integer.parseInt(br.readLine());
                    
                    if(codOpcao == 1){
                        System.out.println("\n\nEntre com o nome destinatário:");
                        String nome = br.readLine();
                        System.out.println("\n\nEntre com o email destinatário:");
                        String email = br.readLine();
                        System.out.println("\n\nAguarde enviando...");
                        sendRelatorioEmail(nome,email);
                    }
                    break;
                
                case 2:
                    a = false;
                    System.out.println("Sessao Encerrada!");
                    break;
            }
        }
    }

    public static void geraRelatorioExcel(String fileName) throws FileNotFoundException, IOException {
        List<Cliente> clientes = new ClienteDAO().Get();
        List<Produto> produtos = new ProdutoDAO().Get();
        List<NotaFiscal> notasFiscais = new NotaFiscalDAO().Get();
        List<NotaFiscalitem> notasFiscaisItem = new NotaFiscalItemDAO().Get();

        FileOutputStream fileOut = new FileOutputStream(fileName + ".xls");
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheetClientes = workbook.createSheet("Clientes");

        for (int i = -1; i < clientes.size(); i++) {
            HSSFRow row = sheetClientes.createRow(i < 0 ? 0 : i + 1);
            row.createCell(0).
                    setCellValue((i < 0
                                    ? "ID"
                                    : clientes.get(i).getId().toString()));
            row.createCell(1)
                    .setCellValue(i < 0
                                    ? "NOME"
                                    : clientes.get(i).getNome());
            row.createCell(2)
                    .setCellValue(i < 0
                                    ? "CPF"
                                    : clientes.get(i).getCpf());
        }

        HSSFSheet sheetProdutos = workbook.createSheet("Produtos");

        for (int i = -1; i < produtos.size(); i++) {
            HSSFRow row = sheetProdutos.createRow(i < 0 ? 0 : i + 1);
            row.createCell(0)
                    .setCellValue(i < 0
                                    ? "ID"
                                    : produtos.get(i).getId().toString());
            row.createCell(1)
                    .setCellValue(i < 0
                                    ? "DESCRICAO"
                                    : produtos.get(i).getDescricao());
        }

        HSSFSheet sheetNotaFiscal = workbook.createSheet("NotaFiscal");

        for (int i = -1; i < notasFiscais.size(); i++) {
            HSSFRow row = sheetNotaFiscal.createRow(i < 0 ? 0 : i + 1);
            row.createCell(0)
                    .setCellValue(i < 0
                                    ? "ID"
                                    : notasFiscais.get(i).getId().toString());
            row.createCell(1)
                    .setCellValue(i < 0
                                    ? "NUMERO"
                                    : notasFiscais.get(i).getNumero() + "");
            row.createCell(2)
                    .setCellValue(i < 0
                                    ? "SERIE"
                                    : notasFiscais.get(i).getSerie().toString());
            row.createCell(3)
                    .setCellValue(i < 0
                                    ? "IDCLIENTE"
                                    : notasFiscais.get(i).getCliente()
                                    .getId().toString());
        }

        HSSFSheet sheetNotaFiscalItem = workbook.createSheet("NotaFiscalItems");

        for (int i = -1; i < notasFiscaisItem.size(); i++) {
            HSSFRow row = sheetNotaFiscalItem.createRow(i < 0 ? 0 : i + 1);
            row.createCell(0)
                    .setCellValue(i < 0
                                    ? "ID"
                                    : notasFiscaisItem.get(i).getId().toString());
            row.createCell(1)
                    .setCellValue(i < 0
                                    ? "VALOR"
                                    : notasFiscaisItem.get(i).getValor() + "");
            row.createCell(2)
                    .setCellValue(i < 0
                                    ? "IDPRODUTO"
                                    : notasFiscaisItem.get(i).getProduto()
                                    .getId().toString());
            row.createCell(3)
                    .setCellValue(i < 0
                                    ? "IDNOTAFISCAL"
                                    : notasFiscaisItem.get(i).getNotaFical()
                                    .getId().toString());
        }

        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }
    
    public static void sendRelatorioEmail(String nome,String to){
        String from = "guilhermeesteves@outlook.com.br";
        final String username = "guilhermeesteves@outlook.com.br";
        final String password = "******";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("Relatorio");

            BodyPart messageBodyPart = new MimeBodyPart();
            
            Date data = new Date();
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            
            messageBodyPart.setText("Olá " + nome + 
                    " segue em anexo relatório - " + f.format(data));

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String filename = "relatorio.xls";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Relatório enviado com suceso..!\n\n\n\n");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
