JamieIL{

	*smartphoneSoundscapes{
		|processingPort=9003|
		var processing = NetAddr.new("127.0.0.1", processingPort);
		var r=255;
		var g=255;
		var b=255;


		OSCdef(\visuals,{
			|msg|
			var sus, legato, dur, cps, delta;
			var sample = "";
			msg.size.do{
				|i|
				if(msg[i] == 'sustain',{sus = msg[i+1]});
				if(msg[i] == 'legato',{legato = msg[i+1]});
				if(msg[i] == 's', {sample = msg[i+1]});
				if(msg[i] == 'cps', {cps = msg[i+1]});
				if(msg[i] == 'delta', {delta = msg[i+1]});
				if(msg[i] == 'backgroundR', {r = msg[i+1]});
				if(msg[i] == 'backgroundG', {g = msg[i+1]});
				if(msg[i] == 'backgroundB', {b = msg[i+1]});
			};

			if(sus == nil, {
				if (legato !=nil,
					{dur = (1/cps)*delta*legato},
					{dur = (1/cps)*delta}
				);
			},{dur = sus}
			);

			processing.sendMsg("/sound",sample,dur);
			processing.sendMsg("/backgroundColor",r,g,b);
		},path:'/play2',recvPort:57120)
	}
}