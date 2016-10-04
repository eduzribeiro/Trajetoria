classdef setup_function_rt_descartar < handle
   properties
        a
        I
   end
   methods
        function obj =setup_function_rt_descartar(initval)
        
            obj.a=[initval initval initval];
            obj.I=initval;
            
        end
        function a = descartar(obj,ar,L)
            
            if obj.I < L+1
                
                obj.I = obj.I + 1;          
                
            end
            
            if obj.I > L
               
                obj.a = ar;
                
            end
        
            a = obj.a;
            
          end

   end
end