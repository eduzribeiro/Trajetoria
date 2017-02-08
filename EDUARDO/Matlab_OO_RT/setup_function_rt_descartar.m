% Classe que descarta os L primeiros dados
classdef setup_function_rt_descartar < handle
   properties
        a
        I
        L
   end
   methods
       %Construtor inializador do objeto
       % inival: inicia todas as variaveis
        function obj =setup_function_rt_descartar(L)
        
            obj.a=[0 0 0];
            obj.I=0;
            obj.L = L;
            
        end
        
        %ar: input signal  [1x3]
        %return :::
        %a: Sinal retornado [1x3]
        function a = descartar(obj,ar)
            
            if obj.I < obj.L+1
                
                obj.I = obj.I + 1;          
                
            end
            
            if obj.I > obj.L
               
                obj.a = ar;
                
            end
        
            a = obj.a;
            
          end

   end
end