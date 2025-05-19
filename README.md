# Redes
Repositório feito com o intuito de colocar os repositórios da disciplina de rede de computadores do IMD

## 1º Atividade - Comunicação FTP (Servidor-Cliente)
- Nessa implementação, fiz uma lógica de troca de arquivos entre o servidor e o cliente baseado em modelo TCP/IP, o código completo se encontra no diretório "Comunication". Nesse projeto, o cliente recebe arquivos listados em sua tela, que estão no diretório dataServer e passa para o repositório dataClient o protocolo que rodou encima do TCP foi o FTP(File Transfer Protocol).

## 2º Atividade - Comunicação FTP (Requisição para serviço na web)
- Nessa implementação que pode ser encontrada no repositório[https://github.com/raimundomarciano/dim0438_redes_atividades/tree/tcp-client] fizemos um servidor apenas do lado do cliente com a utilização de SSLSockets para garantir a segurança
durante a comunicação. Essa aplicação do cliente faz requisições para a api OpenWeatherMap e tem as seguintes funcionalidades:
1) Buscar pela localização
  - Realiza a busca da cidade pelo seu nome, código do estado e código do país pela norma ISO 3166 e retorna os dados relativo ao tempo nesse local.
2) Buscar pelo código postal
  - Realiza a busca pelo código postal e retorna os dados relativo ao tempo nesse local.
3) Busca pela latitude/longitude
  - Nessa função, ele busca os dados de temperatura e clima do local exato da latitude e longitude que foi passada;
4) Encerrar conexão
  - Termina a conexão TCP/IP do cliente com o serviço web.
