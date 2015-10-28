package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Dica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Intervalo;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;

public class DicaDAO {
	public List<Dica> buscarDicas(Metrica m) {
		Connection cf = ConnectionFactory.getConnection();
		Intervalo i;
		List<Dica> dicas = new ArrayList<Dica>();
		Dica d;
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT * FROM DICAS D, INTERVALO I, METRICA M " + 
								"where I.FK_ID_DICAS = D.ID_DICAS " +  
								"AND (M.SIGLA = ? " + 
								"AND (? between I.VALOR_MINIMO AND I.VALOR_MAXIMO)) "+ 
								"AND M.ID_METRICA = I.FK_ID_METRICA "+ 
								"LIMIT 0, 1000;";
			PreparedStatement preparedStatement;
			try {
				
				preparedStatement = cf.prepareStatement(selectSQL);
				preparedStatement.setString(1, m.getSigla());
				preparedStatement.setFloat(2, m.getValorMetrica());
				
				
				
//				System.out.println(preparedStatement.toString());
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					d = new Dica();
					
					d.setDescricao(rs.getString("TEXTO_DICAS"));
					dicas.add(d);
					
				}

				m.setDicas(dicas);
				cf.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return dicas;
	}
}
