<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="mb-3">
	<label for="name" class="col-form-label">Username:</label> <input
		type="text" class="form-control" id="name" name="name"
		required value="${tmp_user.getNombre()}">
</div>
<div class="mb-3">
	<label for="coins"
		class='col-form-label ${tmp_user.errors.get("coins") != null ? "is-invalid" : "" }'>Monedas:</label>
	<input class="form-control" type="number" id="coins" name="coins"
		required value="${tmp_user.getMonedas()}"></input>
	<div class="invalid-feedback">
		<c:out value='${tmp_user.errors.get("coins")}'></c:out>
	</div>
</div>

<div class="mb-3">
	<label for="time"
		class='col-form-label ${tmp_user.errors.get("time") != null ? "is-invalid" : "" }'>Tiempo:</label>
	<input class="form-control" type="number" id="time" name="time"
		required value="${tmp_user.getTiempoDisponible()}"></input>
	<div class="invalid-feedback">
		<c:out value='${tmp_user.errors.get("time")}'></c:out>
	</div>
</div>
<div class="mb-3">
	<label for="type_fav" class="col-form-label">Tipo atraccion preferida(degustacion/aventura/paisaje):</label> <input
		type="text" class="form-control" id="type_fav" name="type_fav"
		required value="${tmp_user.getAtraccionPreferidaStr()}">
</div>
<div class="mb-3">
	<label for="isAdmin" class="col-form-label">Es Admin(true/false):</label> <input
		type="text" class="form-control" id="isAdmin" name="isAdmin"
		required value="${tmp_user.getAdminStr()}">
</div>
<div class="mb-3">
	<label for="password"
		class='col-form-label ${tmp_user.errors.get("password") != null ? "is-invalid" : "" }'>Contrase�a:</label>
	<input class="form-control" type="password" id="password" name="password"
		required value="${tmp_user.getPassword()}"></input>
	<div class="invalid-feedback">
		<c:out value='${tmp_user.errors.get("password")}'></c:out>
	</div>
</div>

<div>
	<button type="submit" class="btn btn-primary">Guardar</button>
	<a onclick="window.history.back();" class="btn btn-secondary"
		role="button">Cancelar</a>
</div>