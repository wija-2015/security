package org.enset.service;

import java.util.*; 

import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpSession;
import javax.validation.Valid; 

import org.enset.dao.EtudiantRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*; 
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.enset.entities.Etudiant; 

@RestController
public class EtudiantRestService {
@Autowired
private EtudiantRepository etudiantRepositoty;

@Secured(value={"ROLE_ADMIN","ROLE_SCOLARITE"})  //les roles qui ont le droit d'utiliser cette methode
@RequestMapping(value="/saveEtudiants",method=RequestMethod.POST)  //@valid pour valider les champs , sinon bindingResult va faire un traitement de la non validation des champs
public Object saveEtudiants(@RequestBody @Valid Etudiant e, BindingResult bindingResult){
	if(bindingResult.hasErrors()){
		Map<String, Object> errors=new HashMap<>();
		errors.put("errors", true);
		for(FieldError fe:bindingResult.getFieldErrors()){
			errors.put(fe.getField(), fe.getDefaultMessage());
		}
		return errors;
		}
	return etudiantRepositoty.save(e);
}

@Secured(value={"ROLE_ADMIN","ROLE_SCOLARITE","ROLE_PROF","ROLE_ETUDIANT"})
@RequestMapping(value="/etudiants",method=RequestMethod.GET)
public Page<Etudiant> listEtudiants(int page, int size){
return etudiantRepositoty.findAll(new PageRequest(page, size));
}

//Methode pour recuperer le user qui est dans la session
@RequestMapping(value="/getLogedUser")
public Map<String, Object> getLogedUser(HttpServletRequest httpServletRequest){
HttpSession httpSession=httpServletRequest.getSession();
SecurityContext securityContext=(SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
String userName=securityContext.getAuthentication().getName();

List<String> roles=new ArrayList<String>();
for(GrantedAuthority ga : securityContext.getAuthentication().getAuthorities()){
roles.add(ga.getAuthority());
}

Map<String, Object> params=new HashMap<>();
params.put("username", userName);
params.put("roles", roles);
return params;
}
}