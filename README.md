# zoe-public
If you want to delete, create or forgot the ADMIN password, please contact the person in charge of the project.

*Projeto público aonde não existe risco de exposição a dados sensíveis, algumas funcionalidades retiradas para fins de segurança, o objetivo desta é incrementar o portfólio.*

Este projeto foi feito para uma empresa real de entregas chamada Zoe, aonde conta com geração de número de rastreio com as iniciais da empresa, busca de pedido pelo rastreio, ou pelo id do pedido.

Envio de email com o código de rastreio, confirmação de endereço do rementente e destinatário, checkagem de status do pedido, valor a pagar etc (bom, na verdade, nessa parte você mesmo pode escolher a personalização).


Seguindo os padrões MVC. Api feita em Springboot 3.0, Spring-security e banco de dados Postgres. Usei do basic auth para gerenciar um único administrador do sistema, pois esse serviço de delivery, por regra de negócio, não implementa cadastro e login de usuário.

*Projeto free source, sinta-se a vontade para usar, copiar, modificar ou revender*
