"""
    Classe para automatizar o teste do log do programa TestWriterReader,
    o qual implementa o padrão leitores-escritores com prioridade para escrita

    Imprime uma saída de erro, caso encontre alguma inconsistência;
    ou imprime nada, caso esteja tudo OK.
"""
class LE:
    def __init__(self):
        self.leitores = 0
        self.escritores = 0
        self.escritores_aguardando = set()

    def leitor_bloqueado(self, id):
        """ Recebe o id do leitor. Verifica se a decisão de bloqueio está correta. """
        if self.escritores == 0 and len(self.escritores_aguardando) == 0:
            print(
                "ERRO: leitor {} bloqueado quando não há escritores ou escritores aguardando!".format(
                    id
                )
            )

    def escritor_bloqueado(self, id):
        """ Recebe o id do escritor. Verifica se a decisão de bloqueio está correta. """
        if self.escritores == 0 and self.leitores == 0:
            print(
                "ERRO: escritor {} bloqueado quando não há escritores nem leitores!".format(
                    id
                )
            )
        # adiciona escritor aguardando
        self.escritores_aguardando.add(id)

    def leitor_lendo(self, id):
        """ Recebe o id do leitor, verifica se pode ler e registra que está lendo. """
        if self.escritores > 0 or len(self.escritores_aguardando) > 0:
            print(f"{self.escritores}, {self.escritores_aguardando}")
            print(
                "ERRO: leitor {} está lendo quando há escritor escrevendo ou escritores aguardando na fila!".format(id)
            )
        self.leitores += 1

    def escritor_escrevendo(self, id):
        if self.escritores > 0 or self.leitores > 0:
            print(
                "ERRO: escritor {} está escrevendo quando há outro escritor ou leitores!"
            )
        self.escritores += 1
        # remove escritor aguardando
        self.escritores_aguardando.discard(id)

    def leitor_saindo(self, id):
        """ Recebe o id do leitor e registra que terminou a leitura. """
        self.leitores -= 1

    def escritor_saindo(self, id):
        """ Recebe o id do escritor e registra que terminou a leitura. """
        self.escritores -= 1
