% Classe que realiza as configura√ß√µes iniciais do dispositivo
classdef setup < handle
   properties
       
	ruido0
	a
	g0
	w
    w0
    L
    
    RL
    
    %ACC
    DES
    ML

    %GIRO
    DESG
    MLG
    
   end
   methods
   % Construtor inializador do objeto
   % inival: inicia todas as variaveis
        function obj =setup(L)

        obj.RL = setup_function_rt_ruido_l(L);
        obj.DES = setup_function_rt_descartar(L);
        obj.ML = setup_function_rt_media_l(L);

        obj.DESG = setup_function_rt_descartar(L);
        obj.MLG = setup_function_rt_media_l(L);

        
        obj.ruido0 = 0;
        obj.a = [0 0 0];
        obj.g0 = [0 0 0];
	    obj.w = [0 0 0];
        obj.w0 = [0 0 0];
        
        end
    %ar: input signal  [1x3]
	%wr: input signal  [1x3]
    %return :::
	%ruido0: Ruido dos L primeiros [1x1]	
	%a: AceleraÁ„o apÛs descarte dos L primeiros [1x3]        
	%g0: MÈdia dos L primeiros [1x3]
	%w: GirÙmetro subtraindo os L primeiros [1x3]
        function [ruido0, a, g0, w] = setup_rt(obj,ar,wr)
            
        %Obtendo dados para calibraÁ„o inicial do acelerÙmetro

    	obj.ruido0 = obj.RL.ruido_de_l(ar');

        obj.g0 = obj.ML.media_de_l(ar);	
	
        obj.a = obj.DES.descartar(ar);

        %Obtendo dados para calibraÁ„o inicial do girÙmetro

    	obj.w0 = obj.MLG.media_de_l(wr);	
	
        obj.w = obj.DESG.descartar(wr);

        %Subtrair w0 de w

        obj.w = obj.w - obj.w0;

        ruido0 = obj.ruido0;
        a = obj.a;
        g0 = obj.g0;
        w = obj.w;
                
        end                   
        
   end
end
