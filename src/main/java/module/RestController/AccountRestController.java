// package module.RestController;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import module.DAO.AccountDAO;
// import module.DAO.AccountRoleDAO;
// import module.DAO.RoleDAO;
// import module.Domain.Account;
// import module.Domain.AccountRoles;
// import module.Domain.Role;

// @CrossOrigin("*")
// @RestController
// @RequestMapping("restAccount")
// public class AccountRestController {
// 	@Autowired
// 	BCryptPasswordEncoder bCryptPasswordEncoder;
// 	@Autowired
// 	AccountDAO aDao;
// 	@Autowired
// 	AccountRoleDAO Dao;
// 	@Autowired
// 	RoleDAO roleDao;

// 	@GetMapping("/accounts")
// 	public ResponseEntity<List<Account>> getAll() {

// 		return ResponseEntity.ok(aDao.findAll());
// 	}

// 	@GetMapping("/accountRole")
// 	public ResponseEntity<List<AccountRoles>> getAllAccRole() {

// 		return ResponseEntity.ok(Dao.findAll());
// 	}

// 	@GetMapping("/Role")
// 	public ResponseEntity<List<Role>> getRole() {
// 		return ResponseEntity.ok(roleDao.findAll());
// 	}

// 	@GetMapping("/accounts/{username}")
// 	public ResponseEntity<?> getOne(@PathVariable("username") String username) {
// 		Optional<Account> accountOptional = aDao.findById(username);

// 		if (accountOptional.isEmpty()) {
// 			return ResponseEntity.status(HttpStatus.NOT_FOUND)
// 					.body("User with username '" + username + "' not found.");
// 		}

// 		Account account = accountOptional.get();
// 		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

// 		return ResponseEntity.ok(account);
// 	}

// 	@GetMapping("/accountss/{username}")
// 	public ResponseEntity<Account> getOneAccount(@PathVariable("username") String username) {
// 		if (!aDao.existsById(username)) {
// 			return ResponseEntity.notFound().build();
// 		}

// 		return ResponseEntity.ok(aDao.findById(username).get());
// 	}

// 	@GetMapping("/Role/USER")
// 	public ResponseEntity<Role> getOneRole() {
// 		return ResponseEntity.ok(roleDao.findById("USER").get());
// 	}

// 	@PostMapping("/accounts")
// 	public ResponseEntity<Account> Post(@RequestBody Account account) {
// 		if (aDao.existsById(account.getEmail())) {
// 			return ResponseEntity.badRequest().build();
// 		}

// 		aDao.save(account);

// 		return ResponseEntity.ok(account);
// 	}

// 	@PostMapping("/accountRole")
// 	public ResponseEntity<AccountRoles> PostAccRole(@RequestBody AccountRoles accountRole) {
// 		Dao.save(accountRole);
// 		return ResponseEntity.ok(accountRole);
// 	}

// 	@PutMapping("/accounts/{username}")
// 	public ResponseEntity<Account> Put(@PathVariable("username") String username, @RequestBody Account account) {
// 		if (!aDao.existsById(username)) {
// 			return ResponseEntity.notFound().build();
// 		}
// 		aDao.save(account);
// 		return ResponseEntity.ok(account);
// 	}

// 	@DeleteMapping("/accounts/{username}")
// 	public ResponseEntity<Map<String, String>> Delete(@PathVariable("username") String username) {
// 		if (!aDao.existsById(username)) {
// 			return ResponseEntity.notFound().build();
// 		}
// 		aDao.deleteById(username);
// 		return ResponseEntity.ok(Map.of("message", "User deleted"));

// 	}

// 	@DeleteMapping("/accountRole/{id}")
// 	public ResponseEntity<Void> DeleteAccRole(@PathVariable("id") Integer id) {
// 		if (!Dao.existsById(id)) {
// 			return ResponseEntity.notFound().build();
// 		}
// 		Dao.deleteById(id);
// 		return ResponseEntity.ok().build();
// 	}

// }

package module.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import module.DAO.AccountDAO;
import module.DAO.AccountRoleDAO;
import module.DAO.RoleDAO;
import module.Domain.Account;
import module.Domain.AccountRoles;
import module.Domain.Role;

@CrossOrigin("*")
@RestController
@RequestMapping("restAccount")
public class AccountRestController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AccountDAO aDao;

	@Autowired
	AccountRoleDAO accountRoleDao;

	@Autowired
	RoleDAO roleDao;

	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = aDao.findAll();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleDao.findAll();
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/accountRoles")
	public ResponseEntity<List<AccountRoles>> getAllAccountRoles() {
		List<AccountRoles> accountRoles = accountRoleDao.findAll();
		return ResponseEntity.ok(accountRoles);
	}

	@GetMapping("/accounts/{username}")
	public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
		Optional<Account> accountOptional = aDao.findById(username);
		if (accountOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "User with username '" + username + "' not found."));
		}

		Account account = accountOptional.get();
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
		return ResponseEntity.ok(account);
	}

	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount(@RequestBody Account account) {
		if (aDao.existsById(account.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(Map.of("message", "Account with email '" + account.getEmail() + "' already exists."));
		}

		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
		Account savedAccount = aDao.save(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
	}

	@PutMapping("/accounts/{username}")
	public ResponseEntity<?> updateAccount(@PathVariable("username") String username, @RequestBody Account account) {
		if (!aDao.existsById(username)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "User with username '" + username + "' not found."));
		}

		account.setEmail(username);
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
		Account updatedAccount = aDao.save(account);
		return ResponseEntity.ok(updatedAccount);
	}

	@DeleteMapping("/accounts/{username}")
	public ResponseEntity<?> deleteAccount(@PathVariable("username") String username) {
		if (!aDao.existsById(username)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "User with username '" + username + "' not found."));
		}

		aDao.deleteById(username);
		return ResponseEntity.ok(Map.of("message", "User with username '" + username + "' deleted successfully."));
	}

	@PostMapping("/accountRoles")
	public ResponseEntity<AccountRoles> createAccountRole(@RequestBody AccountRoles accountRole) {
		AccountRoles savedRole = accountRoleDao.save(accountRole);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
	}

	@DeleteMapping("/accountRoles/{id}")
	public ResponseEntity<?> deleteAccountRole(@PathVariable("id") Integer id) {
		if (!accountRoleDao.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "AccountRole with id '" + id + "' not found."));
		}

		accountRoleDao.deleteById(id);
		return ResponseEntity.ok(Map.of("message", "AccountRole with id '" + id + "' deleted successfully."));
	}

	@GetMapping("/roles/USER")
	public ResponseEntity<?> getUserRole() {
		Optional<Role> roleOptional = roleDao.findById("USER");
		if (roleOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "Role 'USER' not found."));
		}

		return ResponseEntity.ok(roleOptional.get());
	}
}
