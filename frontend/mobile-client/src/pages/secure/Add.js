import { useNavigate } from 'react-router-dom';
import style from './Add.module.css';

function Add() {
  const navigate = useNavigate();

  return (
    <div onClick={() => navigate('/products')} className={style.Add}>
      <span>&#43;</span>
    </div>
  );
}

export default Add;
