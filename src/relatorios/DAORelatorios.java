/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorios;

import conexoes.ConexaoMySql;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Administrador
 */
public class DAORelatorios extends ConexaoMySql {

    public boolean gerarRelatorioCliente() {
        try {
            this.conectar();
            this.executarSQL("SELECT * FROM cliente;");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioClientes.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioProdutos() {
        try {
            this.conectar();
            this.executarSQL("SELECT * FROM produto;");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioProdutos.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioFornecedores() {
        try {
            this.conectar();
            this.executarSQL("SELECT * FROM fornecedor;");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioFornecedores.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean gerarRelatorioVenda(Long pCodigo) {
        try {
            this.conectar();
            this.executarSQL("SELECT "
                    + "     cliente.ID AS cliente_ID, "
                    + "     cliente.BAIRRO AS cliente_BAIRRO, "
                    + "     cliente.CEP AS cliente_CEP, "
                    + "     cliente.CIDADE AS cliente_CIDADE, "
                    + "     cliente.ENDERECO AS cliente_ENDERECO, "
                    + "     cliente.NOME AS cliente_NOME, "
                    + "     cliente.TELEFONE AS cliente_TELEFONE, "
                    + "     cliente.UF AS cliente_UF, "
                    + "     produto.ID AS produto_ID, "
                    + "     produto.ESTOQUE AS produto_ESTOQUE, "
                    + "     produto.FORNECEDORESCODIGO AS produto_FORNECEDORESCODIGO, "
                    + "     produto.NOME AS produto_NOME, "
                    + "     produto.VALOR AS produto_VALOR, "
                    + "     produtodavenda.ID AS produtodavenda_ID, "
                    + "     produtodavenda.IDPRODUTO AS produtodavenda_IDPRODUTO, "
                    + "     produtodavenda.IDVENDA AS produtodavenda_IDVENDA, "
                    + "     produtodavenda.QUANTIDADE AS produtodavenda_QUANTIDADE, "
                    + "     venda.ID AS venda_ID, "
                    + "     venda.CLIENTEID AS venda_CLIENTEID, "
                    + "     venda.DATAVENDA AS venda_DATAVENDA, "
                    + "     venda.DESCONTO AS venda_DESCONTO, "
                    + "     venda.VALORTOTAL AS venda_VALORTOTAL "
                    + " FROM  "
                    + "     cliente cliente INNER JOIN venda venda ON cliente.ID = venda.CLIENTEID "
                    + "     INNER JOIN produtodavenda produtodavenda ON venda.ID = produtodavenda.IDVENDA "
                    + "     INNER JOIN produto produto ON produtodavenda.IDPRODUTO = produto.ID where venda.ID = '" + pCodigo + "'");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioVendas.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
