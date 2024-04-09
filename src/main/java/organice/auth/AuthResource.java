package organice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Auth", description = "API de Autentificação")
public class AuthResource implements AuthController {

    @Autowired
    private AuthService authService;

    @Override
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário e retorna o objeto criado com seu ID.")
    public ResponseEntity<?> create(RegisterIn in) {

        final String id = authService.register(Register.builder()
            .name(in.name())
            .email(in.email())
            .password(in.password())
            .build()
        );

        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri())
            .build();
    }

    @Override
    @Operation(summary = "Faz login de um usuário", description = "Faz login de um usuário e retorna o token")
    public ResponseEntity<LoginOut> authenticate(CredentialIn in) {
        return ResponseEntity.ok(authService.authenticate(in.email(), in.password()));
    }

    @Override
    @Operation(summary = "Verifica o Token", description = "Verifica o token e retorna informações de usuário")
    public ResponseEntity<SolveOut> solve(SolveIn in) {
        final Token token = authService.solve(in.token());
        return ResponseEntity.ok(
            SolveOut.builder()
                .id(token.id())
                .name(token.name())
                .role(token.role())
                .build()
        );
    }

}
