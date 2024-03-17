import { useEffect, useState } from 'react';
import './App.css';
import { getAllCollaborators, getListCommits } from './libs/octokit';

function App() {
  const [commits, setCommits] = useState(null);
  const [collaborators, setCollaborators] = useState(null);

  useEffect(() => {
    //TODO:
    // octokit.rest을 하나의 모듈로 지정해서 사용하고싶음.
    const listCommits = getListCommits('Kimtaewon9483', 'study');
    // 비동기 함수를 콜백으로 처리하기엔 힘드니 async await로 함수화 시키고싶음.
    listCommits.then(res => res)
    .then(data => {
      if (!data) return;
      const commitList = data.data.map(item => item.commit);
      setCommits(commitList);
    })
    .catch(err => {
      throw new Error(err);
    });

    const listCollaborators = getAllCollaborators('Kimtaewon9483', 'study');

    listCollaborators.then(res => res)
    .then(collaborators => {
      if(!collaborators.data) return;
      setCollaborators(collaborators.data);
    })
    .catch(err => {
      throw new Error(err);
    });

  }, []);

  return (
    <div className="h-screen">
      <div className='h-3/4 overflow-scroll'>
        <li>
          {
            commits && commits.map(commit => (
              <li>{commit.message}</li>
            ))
          }
        </li>
      </div>
      <div className='h-1/4 overflow-x-scroll border border-solid'>
        <h2>Users</h2>
        <ul className='grid grid-rows-1 grid-flow-col gap-4'>
          {
            collaborators && collaborators.map(collaborator => (
              <li key={collaborator.login}>
                <div className='w-12 h-12 ml-auto mr-auto border rounded-full overflow-hidden'>
                  <img src={collaborator.avatar_url} alt="No img url" />
                </div>
                <div className='text-sm text-center'>{collaborator.login}</div>
              </li>
            ))
          }
        </ul>
      </div>
    </div>
  );
}

export default App;
