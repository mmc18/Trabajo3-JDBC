package lsi.ubu.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import lsi.ubu.excepciones.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lsi.ubu.util.PoolDeConexiones;

public class ServicioImpl implements Servicio {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioImpl.class);

	@Override
	public void anularBillete(Time hora, java.util.Date fecha, String origen, String destino, int nroPlazas, int ticket)
			throws SQLException {
		PoolDeConexiones pool = PoolDeConexiones.getInstance();

		/* Conversiones de fechas y horas */
		java.sql.Date fechaSqlDate = new java.sql.Date(fecha.getTime());
		java.sql.Timestamp horaTimestamp = new java.sql.Timestamp(hora.getTime());
		/*
		 * int idx=1; Connection con = pool.getConnection(); PreparedStatement st =
		 * con.prepareStatement("select idViaje from tickets where idTicket = ?");
		 * st.setInt(1, ticket); ResultSet rs = st.executeQuery();
		 * System.out.println(rs);
		 */

		// A completar por el alumno
	}

	@Override
	public void comprarBillete(Time hora, Date fecha, String origen, String destino, int nroPlazas)
			throws SQLException {
		PoolDeConexiones pool = PoolDeConexiones.getInstance();
		java.sql.Date fechaSqlDate = new java.sql.Date(fecha.getTime());
		java.sql.Timestamp horaTimestamp = new java.sql.Timestamp(hora.getTime());
		String horaAux = ("01/01/0001 " + horaTimestamp.toString().substring(11, 16));
		int idx = 1;
		Connection con = pool.getConnection();
		PreparedStatement st1 = con.prepareStatement("select * from recorridos where estacionOrigen = ? and estacionDestino = ? and horaSalida = TO_TIMESTAMP(?, 'DD/MM/YYYY HH24:MI')");
		st1.setString(idx++, origen);
		st1.setString(idx++, destino);
		st1.setString(idx++, horaAux);
		ResultSet sol = st1.executeQuery();
		if (sol.next()) {
			int idRecorrido = sol.getInt("idRecorrido");
			int precio = sol.getInt("precio");
			idx = 1;
			st1 = con.prepareStatement("select * from viajes where idRecorrido = ? and fecha = ?");
			st1.setInt(idx++, idRecorrido);
			st1.setDate(idx++, fechaSqlDate);
			sol = st1.executeQuery();
			if (sol.next()) {
				int idViaje = sol.getInt("idViaje");
				int asientosLibres = sol.getInt("nPlazasLibres");
				if (asientosLibres - nroPlazas >= 0) {
					idx = 1;
					st1 = con.prepareStatement("insert into tickets (idTicket, idViaje, fechaCompra, cantidad, precio) values (seq_tickets.nextval, ?, CURRENT_DATE, ?, ?)");
					st1.setInt(idx++, idViaje);
					st1.setInt(idx++, nroPlazas);
					st1.setInt(idx++, nroPlazas*precio);
					st1.executeUpdate();
					idx = 1;
					st1 = con.prepareStatement("update viajes set nPlazasLibres = (nPlazasLibres - ?) where idViaje = ?");
					st1.setInt(idx++, nroPlazas);
					st1.setInt(idx++, idViaje);
					st1.executeUpdate();
					con.commit();
				}else {
					throw new CompraBilleteTrenException(1);
				}
			} else {
				throw new CompraBilleteTrenException(2);
			}
		} else {
			throw new CompraBilleteTrenException(2);
		}
	}
}
