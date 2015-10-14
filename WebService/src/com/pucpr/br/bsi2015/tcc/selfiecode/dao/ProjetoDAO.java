package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class ProjetoDAO {
	
//	SELECT * FROM CODIGO_METRICA CM, CODIGOFONTE CF, METRICA M 
//	WHERE CF.ID_CODIGO = CM.FK_ID_CODIGO
//	AND CM.FK_ID_METRICA = M.ID_METRICA
//	AND CF.FK_US_PROJ = 12
//	ORDER BY FK_ID_CODIGO;
	
	private List<Projeto> projetos;
	
	public List<Projeto> selectProjetos(Usuario usuario) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM USUARIO_PROJETO, PROJETO where FK_PROJETO = ID_PROJETO AND FK_USUARIO = ? AND Ativo = 0";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				//JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
				//preparedStatement.setLong(1, 7700147981l);
				preparedStatement.setLong(1, usuario.getCpf());
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					projeto = new Projeto();
					projeto.setId(rs.getInt("ID_PROJETO"));
					projeto.setNome(rs.getString("NomeProjeto"));
					projeto.setDescricao(rs.getString("Descricao"));
					projeto.setInicio(rs.getDate("Inicio"));
					projeto.setFim(rs.getDate("Fim"));
					projeto.setStatus(rs.getString("Status_projeto"));
					projeto.setTempoParaColeta(rs.getInt("Tempo_Coleta"));
					
					System.out.println(projeto.getNome());
					
					
					projetos.add(projeto);
									
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return projetos;
	}
	
	public Projeto selectProjeto(Projeto proj) {
		Projeto projeto = null;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM PROJETO where  ID_PROJETO = ? AND Ativo = 0";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				//JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
				//preparedStatement.setLong(1, 7700147981l);
				preparedStatement.setInt(1, proj.getId());
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					projeto = new Projeto();
					projeto.setId(rs.getInt("ID_PROJETO"));
					projeto.setNome(rs.getString("NomeProjeto"));
					projeto.setDescricao(rs.getString("Descricao"));
					projeto.setInicio(rs.getDate("Inicio"));
					projeto.setFim(rs.getDate("Fim"));
					projeto.setStatus(rs.getString("Status_projeto"));

				
									
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return projeto;
	}
	
	public boolean inserirGerProj(Usuario usuario, Projeto proj) {

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "INSERT INTO USUARIO_PROJETO ( FK_USUARIO, FK_PROJETO) VALUES (?, ?)";
			
			PreparedStatement preparedStatement;
			try {
				
			
				preparedStatement = cf.prepareStatement(selectSQL);
				
				preparedStatement.setLong(1, usuario.getCpf());
				preparedStatement.setInt(2, proj.getId());
				
				int rs = preparedStatement.executeUpdate();
				if(rs > 0)
				{
					return true;
				}
				else
				{
					return false;
				}

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public int inserDevProj(Desenvolvedor usuario) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "INSERT INTO USUARIO_PROJETO ( FK_USUARIO, FK_PROJETO) VALUES (?, ?)";
			
			PreparedStatement preparedStatement;
			try {
				for (int i = 0; i < usuario.getProjetos().size(); i++) {
					
					projeto = usuario.getProjetos().get(i);
					
					preparedStatement = cf.prepareStatement(selectSQL);
					
					preparedStatement.setLong(1, usuario.getCpf());
					preparedStatement.setInt(2, (projeto.getId()));
					
					preparedStatement.executeUpdate();
					
				}
				
				return 0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return 1;
				//e.printStackTrace();
			}

		}
		return 1;

	}
	
	public int checkDevProj(Desenvolvedor usuario) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "SELECT * FROM USUARIO_PROJETO WHERE FK_USUARIO = ? AND FK_PROJETO = ?";
			
			PreparedStatement preparedStatement;
			try {
				for (int i = 0; i < usuario.getProjetos().size(); i++) {
					
					projeto = usuario.getProjetos().get(i);
					
					preparedStatement = cf.prepareStatement(selectSQL);
					
					preparedStatement.setLong(1, usuario.getCpf());
					preparedStatement.setInt(2, (projeto.getId()));
					
					ResultSet rs = preparedStatement.executeQuery();
					while(rs.next())
						return 0;
					
				}
				cf.close();
				return 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return 1;
				//e.printStackTrace();
			}

		}
		return 1;

	}
	
	public boolean inserirProjeto(Projeto proj) {
		
		java.sql.Date dataIni = new Date(proj.getInicio().getTime());
		java.sql.Date dataFim = new Date(proj.getFim().getTime());
		
		Connection cf = ConnectionFactory.getConnection();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "INSERT INTO PROJETO ( NomeProjeto, Descricao, Inicio, Fim, Status_projeto, Ativo  ) VALUES (?, ?, ?, ?, ?, 0 )";
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, proj.getNome());
				preparedStatement.setString(2, proj.getDescricao());
				preparedStatement.setDate(3, dataIni);
				preparedStatement.setDate(4, dataFim);
				preparedStatement.setString(5, proj.getStatus());
				
				int rs = preparedStatement.executeUpdate();
				ResultSet rsKeys = preparedStatement.getGeneratedKeys();
				
				if (rsKeys.next()) {
				    proj.setId(rsKeys.getInt(1));
				}
				if(rs > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}
	
	public boolean alterarProjeto(Projeto proj) {
		
		java.sql.Date dataIni = new Date(proj.getInicio().getTime());
		java.sql.Date dataFim = new Date(proj.getFim().getTime());
		
		Connection cf = ConnectionFactory.getConnection();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "UPDATE PROJETO SET NomeProjeto = ?, Descricao = ?, Inicio = ?, Fim= ?, Status_projeto = ? WHERE ID_PROJETO = ? ";
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, proj.getNome());
				preparedStatement.setString(2, proj.getDescricao());
				preparedStatement.setDate(3, dataIni);
				preparedStatement.setDate(4, dataFim);
				preparedStatement.setString(5, proj.getStatus());
				preparedStatement.setInt(6, proj.getId());
				
				int rs = preparedStatement.executeUpdate();
				ResultSet rsKeys = preparedStatement.getGeneratedKeys();
				
				if (rsKeys.next()) {
				    proj.setId(rsKeys.getInt(1));
				}
				if(rs > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	public boolean excluirProj(Projeto proj) {
		Connection cf = ConnectionFactory.getConnection();
		 
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "UPDATE PROJETO SET Ativo= 1 WHERE ID_PROJETO = ?";


			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, proj.getId());
				
				int rs = preparedStatement.executeUpdate();
				cf.close();
				if(rs > 0)
					return true;
				else
					return false;
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}


	
}
