% Classe que calcula o ruído dos L primeiros dados
classdef setup_function_rt_ruido_l < handle
   properties
        ruido0
        I
        Si
        L
        F
        JJ
   end
   methods
       %Construtor inializador do objeto
       % inival: inicia todas as variaveis
        function obj =setup_function_rt_ruido_l(L)
        
            obj.ruido0=0;
            obj.I=0;
            obj.Si=[0;0;0];
            obj.L=L;
            obj.JJ = 0;
            
            [H,G] = fir1(16,1/100,'low');          
            obj.F=FiltroIIR(H,G);
        end
        
        %ar: input signal  [3x1]
        %return :::
        %ruido0: Ruido dos L primeiros [1x1]
        function ruido0=ruido_de_l(obj,ar)
            
            if obj.I < obj.L+1
                
                ar = ar - obj.F.filter_rt(ar);
                
                if obj.JJ < 32
                   
                    ar = [0;0;0];
                      
                end
                    
                obj.JJ = obj.JJ + 1;
                
                ar=ar.^2;
                
                obj.Si = obj.Si + ar;                
                obj.I = obj.I + 1;          
                
            end
            
            if obj.I == obj.L+1
               
                
                obj.ruido0 = sqrt(sum(obj.Si/obj.L));
                
            end
            ruido0=obj.ruido0;
          end

   end
end
