class Sensor_Value:
    """
        Tripla registrada pelo sensor
    """
    def __init__(self, id, temperatura):
        self.id = id
        self.temperatura = temperatura
        self.leituras = 0

class Buffer_Circular:
    """
        Buffer circular para guardar os valores registrados
        pelos sensores
    """
    def __init__(self, size):
        self.elements = [None] * size
        self.count = 0
        self.head = 0
        self.tail = -1
        self.capacity = size

    def add(self, value):
        self.tail = (self.tail + 1) % self.capacity
        if self.count == self.capacity:
            self.head = (self.head + 1) % self.capacity
        else:
            self.count += 1
        self.elements[self.tail] = value


    def __iter__(self):
        self.n = self.count
        self.head_it = self.head
        self.tail_it = self.tail
        return self

    def __next__(self):
        if self.n > 0:
            self.n -= 1
            elem = self.elements[self.head_it]
            self.head_it = (self.head_it + 1) % self.capacity
            return elem
        else:
            raise StopIteration
class Estado:
    CONDICAO_NORMAL = 0
    ALERTA_AMARELO = 1
    ALERTA_VERMELHO = 2

class TestaApp:
    """
        Classe para automatizar o teste do log do programa principal,
    """
    def __init__(self):
        self.leitores = 0
        self.escritores = 0
        self.escritores_aguardando = set()
        self.buffer = Buffer_Circular(60)

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

    def sensor_id_temperatura(self, id, temperatura):
        """
            Sensor de id registrando temperatura
            incrementa o identificador de leitura
            Só valores maiores que 30 são registrados no buffer
        """
        for value in self.buffer:
            if value.id == id:
                value.leituras += 1

        if temperatura > 30:
            self.buffer.add(Sensor_Value(id, temperatura))

    def atuador_id_aviso_media(self, id, estado, media):
        """ Atuador (id) mostra estado (condição Normal, alerta Amarelo, alerta Vermelho)
        e a média de temperaturas do sensor de mesmo id
        """
        values = []
        for value in self.buffer:
            if value.id == id:
                values.append(value)


        total = 0
        leituras = 0
        acima_de_35 = 0
        estado_esperado = Estado.CONDICAO_NORMAL

        # Começa percorrendo da última leitura
        for value in reversed(values):
            total += value.temperatura
            if leituras != value.leituras:
                leituras = value.leituras
            leituras += 1

            if value.temperatura > 35:
                acima_de_35 += 1

            if estado_esperado == Estado.CONDICAO_NORMAL:
                if leituras == 5 and acima_de_35 == 5:
                    estado_esperado = Estado.ALERTA_VERMELHO
                elif leituras > 5 and leituras <= 15 and acima_de_35 >= 5:
                    estado_esperado = Estado.ALERTA_AMARELO

        media_esperada = 0
        if total > 0:
            media_esperada = total / len(values)


        # verificar se estado é correto
        if estado != estado_esperado:
            print("ERRO: atuador {} com estado diferente do esperado {}/{}".format(id, estado, estado_esperado))

        # verifica se a média esperada
        if abs(media - media_esperada) > 0.1:
            print("ERRO: atuador {} com média diferente da esperada {}/{}".format(id, media, media_esperada))


