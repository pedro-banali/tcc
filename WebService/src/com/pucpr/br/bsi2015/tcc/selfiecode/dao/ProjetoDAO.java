package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class ProjetoDAO {
	
	private List<Projeto> projetos;
	
	public List<Projeto> selectProjetos(Usuario usuario) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM USUARIO_PROJETO, PROJETO where FK_PROJETO = ID_PROJETO AND FK_USUARIO = ? LIMIT 0, 1000;";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				//JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
				preparedStatement.setLong(1, 7700147981l);
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					projeto = new Projeto();
					projeto.setId(rs.getInt("ID_PROJETO"));
					projeto.setNome(rs.getString("NomeProjeto"));
					projeto.setDescricao(rs.getString("Descricao"));
					projeto.setDataInicio(rs.getDate("Inicio"));
					projeto.setDataFim(rs.getDate("Fim"));
					projeto.setStatus(rs.getString("Status_projeto"));
					
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
	
	public void inserDevProj(Desenvolvedor usuario) {
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
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public boolean inserirProjeto(Projeto proj) {
		
		java.sql.Date dataIni = new Date(proj.getDataInicio().getTime());
		java.sql.Date dataFim = new Date(proj.getDataFim().getTime());
		
		Connection cf = ConnectionFactory.getConnection();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "INSERT INTO PROJETO ( NomeProjeto, Descricao, Inicio, Fim, Status_projeto  ) VALUES (?, ?, ?, ?, ? )";
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				preparedStatement.setString(1, proj.getNome());
				preparedStatement.setString(2, proj.getDescricao());
				preparedStatement.setDate(3, dataIni);
				preparedStatement.setDate(4, dataFim);
				preparedStatement.setString(5, proj.getStatus());
				
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


	
}
