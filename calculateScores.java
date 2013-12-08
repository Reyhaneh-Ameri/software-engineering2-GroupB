	HashMap <String, ArrayList<int[]>> calculateScores(HashMap<String, ArrayList<String>> fields, String startingLetters){
		DatabaseHelper db=new DatabaseHelper();
		db.open(this);
		HashMap<String, ArrayList<int[]>> scores=new HashMap<String, ArrayList<int[]>>();
		Set<String> Names=fields.keySet();
		String[] names=Names.toArray(new String[Names.size()]);
		for (int j=0; j<Names.size(); j++){
			String name=names[j];
			ArrayList<String> s=fields.get(name);
			int[] score;
			score=new int[s.size()];
			//check if first letter is correct
			for (int i=0; i<s.size(); i++){
				String f=s.get(i);
				if (!f.startsWith((String)startingLetters))
					score[i]=0;
				//check if shit is in db
				else if (db.isItThere(i+1, f)){
					score[i]=20;
					//check if anyone has written the same shit
					int dupes=0;
					for (int k=0; k<Names.size(); k++){
						if (k==j)
							continue;
						String nameo=names[k];
						ArrayList<String> so=fields.get(name);
						String fo=s.get(i);
						if (f==fo)
							dupes++;
					}
					if (dupes!=0)
						score[i]=20/(dupes+1);
				}
			}
			ArrayList<int[]> t=new ArrayList<int[]>(Arrays.asList(score));
			scores.put(name, t);
		}
		return scores;
	}

