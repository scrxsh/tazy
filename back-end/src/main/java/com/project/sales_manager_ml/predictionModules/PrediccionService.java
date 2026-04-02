package com.project.sales_manager_ml.predictionModules;

import jakarta.annotation.PostConstruct;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PrediccionService {


    private static final String RUTA_MODEL = "src/main/resources/models/xshells.zip";
    private MultiLayerNetwork modeloPredicciones;

    @Autowired
    VentaDataService ventaDataService;

    @PostConstruct
    public void cargarInicializar(){

        File folder = new File("src/main/resources/nodels");
        if(!folder.exists()){
            folder.mkdirs();
            System.out.println("Carpeta models creada.");
        }
        File archivoModelo = new File(RUTA_MODEL);

        if(archivoModelo.exists()){
            try {
                modeloPredicciones = MultiLayerNetwork.load(archivoModelo, true);
                System.out.println("Modelo cargado desde el servidor..");
            }catch (IOException e){
                System.out.println("Modelo corrupto, re-entrenando la red...");
                inicializarEntrenamiento();
            }
        } else {
            System.out.println("No existe el modelo, entrenando la primera vez...");
            inicializarEntrenamiento();
        }
    }

    private void inicializarEntrenamiento(){
        try {
           inicializarModelo();
           entrenarModelo();
            System.out.println("Modelo entrenado y guardado");
        } catch (IllegalStateException e){
            System.out.println("No se pudo entrenar: " + e.getMessage());
            System.out.println("Llamar manualmente a /api/prediccion/entrenar cuando haya datos.");
        }
    }

    public void inicializarModelo(){
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123456)
                .updater(new Adam(0.001))
                .list()
                .layer(new DenseLayer.Builder().nIn(1).nOut(10)
                        .activation(Activation.RELU)
                        .build())
                .layer(new DenseLayer.Builder().nIn(10).nOut(10)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(10).nOut(1)
                        .build())
                .build();

        modeloPredicciones = new MultiLayerNetwork(conf);
        modeloPredicciones.init();
        modeloPredicciones.setListeners(new ScoreIterationListener(10));
    }

    public void entrenarModelo(){

        try (INDArray datos = ventaDataService.cargarVentas()) {

            INDArray features = datos.getColumn(0).reshape(-1,1);
            INDArray labels = datos.getColumn(1).reshape(-1, 1);

            DataSet dataSet = new DataSet(features, labels);

            for (int i = 0; i < 1000; i++){
                modeloPredicciones.fit(dataSet);
            }
            modeloPredicciones.save(new File(RUTA_MODEL), true);
            System.out.println("Modelo guardado en el servidor.");

        } catch (IOException e){
            throw new RuntimeException("Error al guardar el modelo: " + e.getMessage());
        }
    }

    public double predecirVenta(double precio){

        if (modeloPredicciones == null) {
            throw new IllegalStateException("El modelo no está entrenado. Llama primero a /api/prediccion/entrenar");
        }

        INDArray input = Nd4j.create(new double[][]{{precio}});
        INDArray output = modeloPredicciones.output(input);
        return output.getDouble(0);
    }
}
